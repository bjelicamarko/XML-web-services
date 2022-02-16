import { Doza_detaljnije } from "src/modules/shared/models/Doza";
import { Kontraindikacije } from "src/modules/shared/models/Kontraindikacije";
import { PodaciOLekaru } from "src/modules/shared/models/PodaciOLekaru";

export interface OVakcinaciji {
    Zdravstvena_ustanova: string,
    Punkt: string,
    Podaci_o_lekaru: PodaciOLekaru,
    Doza: Doza_detaljnije,
    Kontraindikacije: Kontraindikacije
}