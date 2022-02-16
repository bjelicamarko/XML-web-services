import { Component } from '@angular/core';
import { ToolbarService, LinkService, ImageService, HtmlEditorService } from '@syncfusion/ej2-angular-richtexteditor';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.scss']
})
export class TestPageComponent {

  public tools: object = {
    items: ['Undo', 'Redo', '|',
        'Bold', 'Italic']
  };
  public iframe: object = { enable: true };
  public height: number = 400;

}
