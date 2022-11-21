

public class Main {
    public static void main(String[] args) {

        PrefixTree t = new PrefixTree();

        t.addAll(
                "cat",
                "rat",
                "rad",
                "bad",
                "noob",
                "nools",
                "fox",
                "fix",
                "list",
                "medium",
                "crispy",
                "crud",
                "croops",
                "java",
                "math",
                "laboratory",
                "nods",
                "noders"
        );
        t.printTree();

        System.out.println(t.getAllWords());
        System.out.println();

    }
}
