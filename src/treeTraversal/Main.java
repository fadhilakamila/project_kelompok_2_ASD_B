package treeTraversal;

public class Main {
    public static void main(String[] args) {

        System.out.println("nyoba1");

        BinaryTree binaryTree = new BinaryTree();

        Node empat = new Node(4);
        Node tujuh = new Node(7);
        Node lima = new Node(5);

        binaryTree.add(empat);
        binaryTree.add(tujuh);
        binaryTree.add(lima);

        System.out.println(binaryTree.root.value);

        // nge-cek siapa yang ada di kirinya root
//        System.out.println(binaryTree.root.left.value);

        // nge-cek siapa yang ada di kanannya root
        System.out.println(binaryTree.root.right.value);

        System.out.println(binaryTree.root.right.left.value);


    }
}