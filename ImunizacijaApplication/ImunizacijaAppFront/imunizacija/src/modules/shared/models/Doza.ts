export interface Doza {
    Redni_broj: {
        "@": {},
        "#": number
    }
    Datum: {
        "@": {},
        "#": string
    }
    Serija: {
        "@": {},
        "#": string
    }
    Proizvodjac: {
        "@": {},
        "#": string
    }
    Tip: {
        "@": {},
        "#": string
    }
}

export interface Doza_detaljnije extends Doza {

    Ekstremitet: {
        "@": {},
        "#": string
    }
    Nezeljena_reakcija: {
        "@": {},
        "#": string
    }
}