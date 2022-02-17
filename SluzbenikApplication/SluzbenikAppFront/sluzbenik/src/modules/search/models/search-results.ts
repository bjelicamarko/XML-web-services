import { DocumentId } from "./document-id";

export interface SearchResults {
    Search_results: {
        Document_id: DocumentId[]
    }
}