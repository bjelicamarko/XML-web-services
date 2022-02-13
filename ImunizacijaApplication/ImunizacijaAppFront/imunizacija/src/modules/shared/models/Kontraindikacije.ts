export interface Kontraindikacije {
    Privremene_kontraindikacije: Kontraindikacija[]
    Odluka_komisije_o_trajnim: string
}

export interface Kontraindikacija {
    Datum: string
    Dijagnoza: string
}