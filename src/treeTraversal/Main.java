package treeTraversal;

public class Main {
    public static void main(String[] args) {
        // Membuat object tree
        BinaryTree binaryTree = new BinaryTree();

        // === CARA 1 ===
        // Membuat node yang membawa value
        Node empat = new Node(4);
        Node tujuh = new Node(7);
        Node lima = new Node(5);
        Node satu = new Node(1);
        Node sepuluh = new Node(10);

        // Memasukkan node ke dalam tree
        binaryTree.add(empat);
        binaryTree.add(tujuh);
        binaryTree.add(lima);
        binaryTree.add(satu);
        binaryTree.add(sepuluh);

        // === CARA 2 ===
        // Membuat node sekaligus memasukkan ke dalam tree
//        binaryTree.root = new Node(1);
//        binaryTree.root.left = new Node(2);
//        binaryTree.root.right = new Node(3);
//        binaryTree.root.left.left = new Node(4);
//        binaryTree.root.left.right = new Node(5);

        System.out.println("=== Pengecekan Node dalam Tree ===");
        // nge-cek siapa root-nya
        System.out.println(binaryTree.root.value);

        // nge-cek left subtree-nya root
        System.out.println(binaryTree.root.left.value);

        // nge-cek right subtree-nya root
        System.out.println(binaryTree.root.right.value);

        // ngecek kirinya right subtree-nya root
        System.out.println(binaryTree.root.right.left.value);

        System.out.println();

        System.out.println("=== Traversal Tree Data Structure dengan Algoritma Depth First Search (DFS) ===");
        System.out.println("Hasil Inorder traversal\t\t:");
        binaryTree.printInorder(binaryTree.root);

        System.out.println("\nHasil Preorder traversal\t:");
        binaryTree.printPreorder(binaryTree.root);

        System.out.println("\nHasil Postorder traversal\t:");
        binaryTree.printPostorder(binaryTree.root);

    }
}