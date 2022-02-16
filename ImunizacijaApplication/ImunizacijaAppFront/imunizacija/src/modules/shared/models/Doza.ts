export interface Doza {
    "@": {
        Redni_broj: number
    }
    'util:Datum': {
        "#": string
    }
    'util:Serija': {
        "#": string
    }
    'util:Proizvodjac': {
        "#": string
    }
    'util:Tip': {
        "#": string
    }
}

export interface Doza_detaljnije extends Doza {

    'util:Ekstremitet': {
        "#": string
    }
    'util:Nezeljena_rekacija': {
        "#": string
    }
}