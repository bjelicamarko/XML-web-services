export interface Kontraindikacije {
    Privremena_kontraindikacija: Kontraindikacija[]
    Odluka_komisije_o_trajnim: string
}

export interface Kontraindikacija {
    Datum: string
    Dijagnoza: string
}