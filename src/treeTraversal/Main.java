package treeTraversal;

public class Main {
    public static void main(String[] args) {

        BinaryTree binaryTree = new BinaryTree();

        Node empat = new Node(4);
        Node tujuh = new Node(7);
        Node lima = new Node(5);
        Node satu = new Node(1);
        Node sepuluh = new Node(10);

        binaryTree.add(empat);
        binaryTree.add(tujuh);
        binaryTree.add(lima);
        binaryTree.add(satu);
        binaryTree.add(sepuluh);

        // nge-cek siapa root-nya
        System.out.println(binaryTree.root.value);

        // nge-cek siapa yang ada di kirinya root
        System.out.println(binaryTree.root.left.value);

        // nge-cek siapa yang ada di kanannya root
        System.out.println(binaryTree.root.right.value);

        System.out.println(binaryTree.root.right.left.value);

        //InOrder
        System.out.println("Inorder traversal of binary tree is");
        binaryTree.printInorder(binaryTree.root);

        // PostOrder
        System.out.println("Postorder traversal of binary tree is ");
        binaryTree.printPostorder(binaryTree.root);



    }
}
