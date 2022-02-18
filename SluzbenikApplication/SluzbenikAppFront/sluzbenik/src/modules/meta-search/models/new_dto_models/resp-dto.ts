export interface RespDTO {
    Search_results: {
        Search_result: {
            Document_id: string[],
            Referencing: { Document_id: string[] }[],
            Referenced_by: { Document_id: string[] }[]
        }[]
    }
}