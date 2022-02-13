import { Component, OnInit } from '@angular/core';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { GradVakcinaKolicinaDTO } from '../../models/GradVakcinaKolicinaDTO';
import { VakcinaDTO } from '../../models/VakcinaDTO';
import { VakcineDTO } from '../../models/VakcineDTO';
import { VaccineService } from '../../services/vaccine.service';

@Component({
  selector: 'app-vaccine-page',
  templateUrl: './vaccine-page.component.html',
  styleUrls: ['./vaccine-page.component.scss']
})
export class VaccinePageComponent {

  options: string[] = ['Becej', 'Subotica', 'Novi Sad', 'Beograd', 'Nis'];

  selectedOption: string = '';

  vakcine: VakcineDTO = {
    Vakcine: {
      $: {
        xmlns: 'http://www.vakc-sistem.rs/termini'
      },
      Vakcina: []
    }
  }

  constructor(private vaccineService: VaccineService, private snackBarService: SnackBarService) { }

  onChange(newValue: any) {
    this.selectedOption = newValue;
    this.vaccineService.getVaccineStatusOfCity(this.selectedOption)
        .subscribe(response => {
          this.vakcine = this.vaccineService.parseXml(response.body as string);
          console.log(this.vakcine);
        })
  }

  changeQuanity(i: number, vakcina: VakcinaDTO) {
    var inputValue = (<HTMLInputElement>document.getElementById(i.toString())).value;

    if (Number(inputValue) <= 0) {
      this.snackBarService.openSnackBar("Nevalidna vrednost!");
    } else {
      vakcina._ = Number(inputValue);
      let gradVakcinaKolicinaDTO : GradVakcinaKolicinaDTO = {
        GradVakcinaKolicina: {
          "@": { xmlns: "http://www.vakc-sistem.rs/termini"},
          grad: this.selectedOption,
          nazivProizvodjaca: vakcina.$.Naziv_proizvodjaca,
          kolicina: vakcina._
        }
      };
  
      this.vaccineService.updateVaccine(gradVakcinaKolicinaDTO)
          .subscribe(response => {
            this.snackBarService.openSnackBar(response.body as string);
          })
    }
    
  }
}
