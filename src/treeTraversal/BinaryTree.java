package treeTraversal;

public class BinaryTree {
    Node root;

    public void add(Node node) {
        root = addRecursive(root, node);
    }

    public Node addRecursive(Node current, Node node) {
        if (current == null) {
            return node;
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
        // Traverse left
        printInorder(node.left);
        // Traverse root
        System.out.print(node.value + " ");
        // Traverse right
        printInorder(node.right);
    }
    public void printPreorder(Node node) {
        if (node == null)
            return;
        // Traverse root
        System.out.print(node.value + " ");
        // Traverse left
        printPreorder(node.left);
        // Traverse right
        printPreorder(node.right);
    }

    public void printPostorder(Node node){
        if (node == null)
            return;

        // Traverse left
        printPostorder(node.left);
        // Traverse right
        printPostorder(node.right);
        // Traverse root
        System.out.print(node.value + " ");
    }

}