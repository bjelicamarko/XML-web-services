import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { DocumentProviderService } from 'src/modules/shared/services/document-provider.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { RespDTO } from '../../models/new_dto_models/resp-dto';
import { MetaSearchService } from '../../services/meta-search.service';

@Component({
  selector: 'app-meta-search-page',
  templateUrl: './meta-search-page.component.html',
  styleUrls: ['./meta-search-page.component.scss']
})
export class MetaSearchPageComponent {

  dokumenti: string[] = ['interesovanje', 'saglasnost', 'zahtev', 'potvrda', 'dzs'];

  izabranDokument: string = 'interesovanje';
  referencedBy: string | undefined;
  referencing: string | undefined;

  postavljenUpit: string = '';

  moguciPredikatiInteresovanje: string = "Moguci predikati: createdWhen.";
  moguciPredikatiSaglasnost: string = "Moguci predikati: createdAt, issuedTo & refBy.";
  moguciPredikatiZahtev: string = "Moguci predikati: createdBy, createdIn & createdWhen.";
  moguciPredikatiPotvrda: string = "Moguci predikati: createdAt, issuedTo & refBy.";
  moguciPredikatiDzs: string = "Moguci predikati: createdAt, createdBy, issuedTo & refBy.";

  moguciPredikati: string = this.moguciPredikatiInteresovanje;

  searchResults: RespDTO | undefined;

  constructor(private metaSearchService: MetaSearchService, private snackBarService: SnackBarService,
    private utilService: UtilService, private documentProviderService: DocumentProviderService,
    private sanitizer: DomSanitizer) { }

  onChange(newValue: any) {
    this.izabranDokument = newValue;
    console.log(this.izabranDokument);
    if (this.izabranDokument === 'interesovanje') {
      this.moguciPredikati = this.moguciPredikatiInteresovanje;
      this.referencedBy = undefined;
      this.referencing = undefined;
    } else if (this.izabranDokument === 'saglasnost') {
      this.moguciPredikati = this.moguciPredikatiSaglasnost;
      this.referencing = 'interesovanje';
      this.referencedBy = 'potvrda';
    } else if (this.izabranDokument === 'zahtev') {
      this.moguciPredikati = this.moguciPredikatiZahtev;
      this.referencedBy = undefined;
      this.referencing = undefined;
    } else if (this.izabranDokument === 'potvrda') {
      this.moguciPredikati = this.moguciPredikatiPotvrda;
      this.referencing = 'saglasnost';
      this.referencedBy = 'dzs';
    } else if (this.izabranDokument === 'dzs') {
      this.moguciPredikati = this.moguciPredikatiDzs;
      this.referencedBy = 'zahtev';
      this.referencing = 'potvrda';
    }
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
            this.searchResults = res;
          else
            this.searchResults = undefined;

          if (!this.searchResults)
            this.snackBarService.openSnackBarFast("Nema rezultata za unetu pretragu");
        },
        (err) => {
          this.snackBarService.openSnackBarFast("Nije validan upit.");
        })
        
    }
  }

  getPdfOnLink(refby: boolean, documentId: string) {
    let documentName: string | undefined = "";
    if (refby) {
      documentName = this.referencedBy?.toLowerCase();
    } else {
      documentName = this.referencing?.toLowerCase();
    }

    if (!documentName) {
      return;
    }

    this.documentProviderService.getDocumentPDF(documentName, documentId).subscribe((response) => {
      if (response.body)
        this.utilService.downloadPDFDocument(response.body, documentName);
    },
      (error) => {
        this.snackBarService.openSnackBarFast("Doslo je do greške prilikom preuzimanja/prikazivanja dokumenta.");
      });
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

  getJson(documentId: any) {
    this.metaSearchService.getStringJson(this.izabranDokument, documentId).subscribe((response) => {
      if (response.body)
        this.utilService.downloadJSONDocument(response.body, this.izabranDokument);
    },
      (error) => {
        this.snackBarService.openSnackBarFast("Doslo je do greške prilikom preuzimanja dokumenta.");
      });
  }

  getRdf(documentId: any) {
    this.metaSearchService.getStringRdf(this.izabranDokument, documentId).subscribe((response) => {
      if (response.body)
        this.utilService.downloadRDFDocument(response.body, this.izabranDokument);
    },
      (error) => {
        this.snackBarService.openSnackBarFast("Doslo je do greške prilikom preuzimanja dokumenta.");
      });
  }

}
