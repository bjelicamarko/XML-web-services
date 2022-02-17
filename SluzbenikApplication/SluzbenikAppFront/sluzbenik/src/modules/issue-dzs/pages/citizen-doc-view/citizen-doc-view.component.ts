import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentProviderService } from 'src/modules/shared/services/document-provider.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { ReasonToDeclineComponent } from '../../components/reason-to-decline/reason-to-decline.component';
import { UserDocsDTO } from '../../models/user-docs-dto';
import { UserViewDTO } from '../../models/user-view-dto';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-citizen-doc-view',
  templateUrl: './citizen-doc-view.component.html',
  styleUrls: ['./citizen-doc-view.component.scss']
})
export class CitizenDocViewComponent implements OnInit {

  public userID: string = "";
  public user: UserViewDTO;
  public userDocsDTO: UserDocsDTO | undefined;

  constructor(private route: ActivatedRoute, private userService: UsersService, private snackBarService: SnackBarService,
    private docProvider: DocumentProviderService, private router: Router, private utilService: UtilService, public dialog: MatDialog,) {
    this.user = { email: "", firstName: "", lastName: "", userID: "" };
  }

  ngOnInit(): void {
    this.userID = this.route.snapshot.params['userID'];
    this.userService.getOne(this.userID).subscribe((res) => {
      if (res.body != null) {
        let resp = res.body;
        this.user = this.userService.userRespfromXMLToObj(resp);
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(err.error);
    });

    this.userService.getDocumentationOfUser(this.userID).subscribe((res) => {
      if (res.body != null) {
        let resp = res.body;
        this.userDocsDTO = this.userService.usersDocRespfromXMLToObj(resp);
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(err.error);
    });
  }

  public openPdf(docType: string, docId: string) {
    this.docProvider.getDocumentPDF(docType, docId).subscribe((resp) => {
      if (resp.body) {
        this.utilService.openPDFDocumentOnly(resp.body);
      }
    });
  }

  public approveDzs() {
    if (!this.userDocsDTO) {
      return;
    }
    if (this.userDocsDTO.documentsOfUser.zahtevDZSList.length === 0) {
      return;
    }
    this.userService.approveDZS(this.userDocsDTO.documentsOfUser.zahtevDZSList[0], this.user.userID, this.user.email).subscribe((resp) => {
      if (resp.body) {
        this.snackBarService.openSnackBar(resp.body);
        this.router.navigate(["sluzbenik-app/dzs/izdavanje"]);
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(err.error);
    });
  }

  public declineDzs() {
    const dialogRef = this.dialog.open(ReasonToDeclineComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (!this.userDocsDTO) {
          return;
        }
        if (this.userDocsDTO.documentsOfUser.zahtevDZSList.length === 0) {
          return;
        }
        this.userService.declineDZS(this.userDocsDTO?.documentsOfUser.zahtevDZSList[0], result, this.user.email).subscribe((resp) => {
          if (resp.body) {
            this.snackBarService.openSnackBar(resp.body);
            this.router.navigate(["sluzbenik-app/dzs/izdavanje"]);
          }
        }, (err) => {
          if (err.error)
            this.snackBarService.openSnackBar(err.error);
        });
      }
    });
  }

}
