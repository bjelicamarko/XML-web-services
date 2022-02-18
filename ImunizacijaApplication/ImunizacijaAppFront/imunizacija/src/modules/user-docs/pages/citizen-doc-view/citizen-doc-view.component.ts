import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentProviderService } from 'src/modules/shared/services/document-provider.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { UserDocsDTO } from '../../models/user-docs-dto';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-citizen-doc-view',
  templateUrl: './citizen-doc-view.component.html',
  styleUrls: ['./citizen-doc-view.component.scss']
})
export class CitizenDocViewComponent implements OnInit {

  public userID: string = "";
  public userDocsDTO: UserDocsDTO | undefined;

  constructor(private route: ActivatedRoute, private userService: UsersService, private snackBarService: SnackBarService,
    private docProvider: DocumentProviderService, private router: Router, private utilService: UtilService, public dialog: MatDialog,) {
    this.userID = utilService.getLoggedUserID();
  }

  ngOnInit(): void {
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
}
