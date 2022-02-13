import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NaslovPoruka } from '../../models/NaslovPoruka';

@Component({
  selector: 'app-conformation-dialog',
  templateUrl: './conformation-dialog.component.html',
  styleUrls: ['./conformation-dialog.component.scss']
})
export class ConformationDialogComponent {

  constructor(public dialogRef: MatDialogRef<ConformationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public naslovPoruka: NaslovPoruka) {}


}
