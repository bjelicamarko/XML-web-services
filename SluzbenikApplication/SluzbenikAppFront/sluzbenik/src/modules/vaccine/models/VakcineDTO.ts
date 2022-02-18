import { VakcinaDTO } from "./VakcinaDTO";

export interface VakcineDTO {
    Vakcine: {
        "$" : { xmlns: string },
        Vakcina: VakcinaDTO[]
    }
}