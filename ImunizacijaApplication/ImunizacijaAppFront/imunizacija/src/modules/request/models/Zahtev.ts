import { LicniPodaciJmbgBrPasosa } from "src/modules/shared/models/LicniPodaci";

export interface Zahtev {
    Zahtev: {
        "@": {}
        Podnosilac: LicniPodaciJmbgBrPasosa,
        Razlog: string,
        Mesto: string,
        Datum: string
    }
}