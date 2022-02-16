import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UserViewDTO } from '../../models/user-view-dto';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.scss']
})
export class UsersViewComponent implements OnInit {

  //table
  public usersList: UserViewDTO[] = [];
  dataSource = new MatTableDataSource(this.usersList);
  displayedColumns: string[] = ['userID', 'firstName', 'lastName', 'email'];

  //pagination
  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  public totalItems: number = 1;
  public pageSize: number = 2;
  currentPage: number = 1;

  //forma
  public searchFormGroup: FormGroup;

  constructor(private fb: FormBuilder, public dialog: MatDialog, private router: Router, private snackBarService: SnackBarService, private usersService: UsersService) {
    this.searchFormGroup = this.fb.group({
      searchField: ['']
    });
  }

  setVisible(): void {
    //this.dataSource.data 
    let firstIndex = (this.currentPage - 1) * this.pageSize;
    let lastIndex = firstIndex + this.pageSize >= this.totalItems ? this.totalItems : firstIndex + this.pageSize;
    this.dataSource.data = this.usersList.slice(firstIndex, lastIndex);
  }

  ngOnInit(): void {
    this.usersService.getAll().subscribe((res) => {
      if (res.body != null) {
        let resp = res.body;
        this.usersList = this.usersService.usersRespfromXMLToObj(resp).users.korisnikBasicInfoDTOList;
        this.totalItems = this.usersList.length;
        this.setVisible();
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(err.error);
    });

    // this.onChanges();
  }

  onChanges(): void {
    // this.searchFormGroup.valueChanges.subscribe(val => {
    //   this.usersService.searchUsers(val.searchField, val.userType, 0, this.pageSize, this.dataSource.sort?.active, this.dataSource.sort?.direction).subscribe((res) => {
    //     if (res.body != null) {
    //       this.dataSource.data = res.body;
    //       this.setPagination(res.headers.get('total-elements'), res.headers.get('current-page'));
    //       if (this.pagination) {
    //         this.pagination.setActivePage(1);
    //       }
    //     }
    //   });
    // }, (err) => {
    //   if (err.error)
    //     this.snackBarService.openSnackBar(String(err.console));
    // });
  }

  public selectedUser(user: UserViewDTO) {
    this.router.navigate(["sluzbenik-app/dzs/dokumenti/" + user.userID]);
    // this.usersService.getUserInfo(user.id).subscribe((res) => {
    //   if (res.body != null) {
    //     const dialogRef = this.dialog.open(ProfileViewComponent, {
    //       data: { user: res.body, isAdmin: true, isWorker: user.userType !== 'ADMINISTRATOR' && user.userType !== 'MANAGER' },
    //       width: '600px',
    //       height: '80vh'
    //     });
    //     dialogRef.afterClosed().subscribe(result => {
    //       if (result) {
    //         this.updateUser(result as UserUpdate);
    //       }
    //     });
    //   }
    // }, (err) => {
    //   if (err.error)
    //     this.snackBarService.openSnackBar(String(err.console));
    // });
  }


  changePage(newPage: number) {
    this.currentPage = newPage;
    this.setVisible();
    // this.usersService.searchUsers(this.searchFormGroup.value.searchField, this.searchFormGroup.value.userType, newPage - 1, this.pageSize, this.dataSource.sort?.active, this.dataSource.sort?.direction).subscribe((res) => {
    //   if (res.body != null) {
    //     this.dataSource.data = res.body;
    //     this.setPagination(res.headers.get('total-elements'), res.headers.get('current-page'));
    //   }
    // }, (err) => {
    //   if (err.error)
    //     this.snackBarService.openSnackBar(String(err.console));
    // });
  }
}
