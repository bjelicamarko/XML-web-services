import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-reason-to-decline',
  templateUrl: './reason-to-decline.component.html',
  styleUrls: ['./reason-to-decline.component.scss']
})
export class ReasonToDeclineComponent implements OnInit {

  public rejectFormGroup: FormGroup;

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<ReasonToDeclineComponent>) {
    this.rejectFormGroup = this.fb.group({
      reason: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

}
