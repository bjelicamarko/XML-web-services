import { LicniPodaciJmbgBrPasosa, Podnosilac } from "src/modules/shared/models/LicniPodaci";

export interface Zahtev {
    Zahtev: {
        "@": {}
        Podnosilac: Podnosilac,
        Razlog: string,
        Mesto: string,
        Datum: string
    }
}