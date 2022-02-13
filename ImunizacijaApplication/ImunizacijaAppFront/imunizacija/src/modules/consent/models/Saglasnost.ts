import { Drzavljanstvo } from "src/modules/shared/models/Drzavljanstvo";
import { Kontakt } from "src/modules/shared/models/Kontakt";
import { LicniPodaci } from "src/modules/shared/models/LicniPodaci";
import { OVakcinaciji } from "./OVakcinaciji";

export interface Saglasnost {
    Saglasnost: {
        "@": {},
        Drzavljanstvo: Drzavljanstvo,
        Licni_podaci: LicniPodaci,
        Kontakt: Kontakt,
        Zaposlenje: { Radni_status: string, Zanimanje: string },
        Izjava: { Saglasan: string, Imunoloski_lek: string },
        Datum: string,
        O_vakcinaciji: OVakcinaciji | null
    }
}