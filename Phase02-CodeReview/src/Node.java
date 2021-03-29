public class Node {
    private String documentId;
    private int indexInDocument;

    Node(String documentId, int indexInDocument) {
        this.documentId = documentId;
        this.indexInDocument = indexInDocument;
    }

    String getDocumentId() {
        return this.documentId;
    }

    @Override
    public int hashCode() {
        return this.documentId.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Node)) {
            return false;
        }
        Node n = (Node) object;
        return this.documentId.equals(n.documentId);
    }
}