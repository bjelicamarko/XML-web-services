import { DocumentId } from "./document-id";

export interface MetaSearchResults {
    Meta_search_results: {
        Document_id: DocumentId[]
    }
}