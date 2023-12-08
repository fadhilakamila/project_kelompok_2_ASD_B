package treeTraversal;

public class BinaryTree {
    Node root;

    // Menambahkan node ke dalam tree
    public void add(Node node) {
        root = addRecursive(root, node);
    }

    // posisi current (root setiap level) dibandingkan dengan yang mau dimasukkan. Node di parameter tsb adalah node yang mau dimasukin
    public Node addRecursive(Node current, Node node) { // method untuk membuat tree baru
        if (current == null) {
            return node; // node di sini dimasukkan ke dalam root di line 8
        } else if (current.value > node.value) {
            current.left = addRecursive(current.left, node);
        } else if (current.value < node.value) {
            current.right = addRecursive(current.right, node);
        } else {
            return current;
        }
        return current;
    }

    public void printInorder(Node node) {
        if (node == null)
            return;

        printInorder(node.left);

        System.out.print(node.value + " ");

        printInorder(node.right);
    }
}
