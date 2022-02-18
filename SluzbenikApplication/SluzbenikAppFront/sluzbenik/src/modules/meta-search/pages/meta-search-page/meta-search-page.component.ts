import { Component, OnInit } from '@angular/core';
import { DocumentProviderService } from 'src/modules/shared/services/document-provider.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { MetaSearchResults } from '../../models/meta-search-results';
import { MetaSearchService } from '../../services/meta-search.service';

@Component({
  selector: 'app-meta-search-page',
  templateUrl: './meta-search-page.component.html',
  styleUrls: ['./meta-search-page.component.scss']
})
export class MetaSearchPageComponent {

  dokumenti: string[] = ['interesovanje', 'saglasnost', 'zahtev', 'potvrda', 'dzs'];

  izabranDokument: string = 'interesovanje';
  postavljenUpit: string = '';

  metaSearchResults: MetaSearchResults | undefined;

  constructor(private metaSearchService: MetaSearchService, private snackBarService: SnackBarService,
    private utilService: UtilService, private documentProviderService: DocumentProviderService) { }

  onChange(newValue: any) {
    this.izabranDokument = newValue;
    console.log(this.izabranDokument);
  }

  search() {
    // (createdAt='2022-01-09'&&issuedTo='213223122')||refBy='djura'
    // ($($createdAt='2022-01-09'$&&$issuedTo='213223122'$)$&&$refBy='djura'$)$||$refBy='pera'$||$($createdAt='2022-01-09'$&&$issuedTo='213223122'$)$
    // ((createdAt='2022-01-09'&&issuedTo='213223122')&&refBy='djura')||refBy='pera'||(createdAt='2022-01-09'&&issuedTo='213223122')
    if (this.postavljenUpit && this.postavljenUpit.length > 0) {
      console.log(this.postavljenUpit);
      let preradjenUpit = this.postavljenUpit;
      preradjenUpit = preradjenUpit.replace(/\(/g, '($');
      preradjenUpit = preradjenUpit.replace(/\)/g, '$)');
      preradjenUpit = preradjenUpit.replace(/\&\&/g, '\$$&&$'); //&&&$
      preradjenUpit = preradjenUpit.replace(/\|\|/g, '$||$');
      preradjenUpit = '$' + preradjenUpit + '$';

      console.log(this.izabranDokument + '~' + preradjenUpit);
      let result = this.izabranDokument + '~' + preradjenUpit;
      this.metaSearchService.advanceSearch(result)
        .subscribe(response => {
          let res = this.metaSearchService.parseXml(response.body as string);

          if (res.Meta_search_results !== '')
            this.metaSearchResults = res.MetaSearchResults;
          else
            this.metaSearchResults = undefined;

          if (!this.metaSearchResults)
            this.snackBarService.openSnackBarFast("Nema rezultata za unetu pretragu");
        })
    }
  }

  getPdf(documentId: any) {
    this.documentProviderService.getDocumentPDF(this.izabranDokument, documentId).subscribe((response) => {
      if (response.body)
        this.utilService.downloadPDFDocument(response.body, this.izabranDokument);
    },
      (error) => {
        this.snackBarService.openSnackBarFast("Doslo je do greške prilikom preuzimanja/prikazivanja dokumenta.");
      });
  }

  getHtml(documentId: any) {
    this.documentProviderService.getDocumentHTML(this.izabranDokument, documentId).subscribe((response) => {
      if (response.body)
        this.utilService.openHtmlDocumentInNewTab(response.body);
    },
      (error) => {
        this.snackBarService.openSnackBarFast("Doslo je do greške prilikom prikazivanja dokumenta.");
      });
  }
}
