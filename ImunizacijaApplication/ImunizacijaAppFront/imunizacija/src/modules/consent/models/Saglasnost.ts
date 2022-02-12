import { Drzavljanstvo } from "src/modules/shared/models/Drzavljanstvo";
import { Kontakt } from "src/modules/shared/models/Kontakt";
import { LicniPodaci } from "src/modules/shared/models/LicniPodaci";

export interface Saglasnost {
    Saglasnost: {
        "@": {},
        Drzavljanstvo: Drzavljanstvo,
        Licni_podaci: LicniPodaci,
        Kontakt: Kontakt,
        Zaposlenje: { Radni_status: '', Zanimanje: '' },
        Izjava: { Saglasan: '', Imunoloski_lek: '' },
        Datum: string
    }
}