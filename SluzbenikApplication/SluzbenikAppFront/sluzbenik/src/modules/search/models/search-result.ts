import { DocumentId } from "./document-id";

export interface SearchResult {
    Document_id: DocumentId,
    Referencing: { Document_id: DocumentId[]},
    Referenced_by: { Document_id: DocumentId[]}
}