import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
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

  constructor(private route: ActivatedRoute, private userService: UsersService, private snackBarService: SnackBarService) { }

  ngOnInit(): void {
    this.userID = this.route.snapshot.params['userID'];
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

}
