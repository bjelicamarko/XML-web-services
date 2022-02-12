import { Component, OnInit } from '@angular/core';
import { ScriptService } from 'src/modules/shared/services/script.service';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.scss']
})
export class TestPageComponent implements OnInit {

  constructor(private scriptService: ScriptService) { 
    this.scriptService.load('xonomy');
  }

  ngOnInit(): void {
   
  }

}
