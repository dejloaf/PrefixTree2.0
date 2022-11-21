import java.util.*;

public class PrefixTree {


    private final Comparator comparator = new Comparator<Node>() {
        @Override
        public int compare(Node s1, Node s2) {
            if (s1.value.length() == s2.value.length())
                for (int i = 0; i < s1.value.length(); i++)
                    if (s1.value.charAt(i) != s2.value.charAt(i))
                        return s2.value.charAt(i) - s1.value.charAt(i);
            return s2.value.length() - s1.value.length();
        }
    };
    class Node {
        String value;
        List<Node> next = null;
        public Node(String value, List<Node> next) {
            this.value = value;
            this.next = next;
        }
        public Node(String value) {
            this.value = value;
            this.next = null;
        }
    }
    class TypeAddition {
        int index = -1;
        boolean splitFlag = false;
        String substring = "";
        public TypeAddition(int index, boolean splitFlag, String substring) {
            this.index = index;
            this.splitFlag = splitFlag;
            this.substring = substring;
        }
    }
    private static int size = 0;
    private static int sizeWords = 0;
    private static Node root = null;

    public Node root() {
        return root;
    }

    public int size(){
        return size;
    }

    public boolean add(String word){
        if(root == null){
            root = new Node("", List.of(new Node(word)));
            size++;
        }else {
            Node tmp = root;
            List<Node> next = tmp.next;
            TypeAddition ta = getTypeAddition(next, word);

            while (ta.index != -1 && !ta.splitFlag){
                tmp = tmp.next.get(ta.index);
                next = tmp.next;
                word = word.substring(tmp.value.length());
                if(next == null)
                    return false;
                ta = getTypeAddition(next, word);
            }
            if(ta.splitFlag && ta.substring.length() != 0){
                splitAdd(tmp.next.get(ta.index), ta.substring.length(), word);
                size++;
                return true;
            }else {
                defultAdd(tmp, word);
                size++;
                return true;
            }
        }

        return false;
    }

    public boolean contains(String word){
        List<String> words = getAllWords();

        for (String s:words) {
            if(s.equals(word))
                return true;
        }
        return false;
    }

    public static List<String> getWordsByPrefix(String prefix){
        List<String> words = getAllWords();
        List<String> result = new ArrayList<>();
        for (String s:words) {
            if(s.indexOf(prefix) == 0)
                result.add(s);
        }
        return result;
    }

    public boolean removeWordsByPrefix(String prefix){

        size-=getWordsByPrefix(prefix).size();
        Node tmp = root;
        TypeAddition ta = getNodeByPrefix(root.next, prefix);
        while (ta.index!=-1){
            prefix = prefix.substring(ta.substring.length());
            if(prefix.length() == 0)
                break;

            tmp = tmp.next.get(ta.index);
            ta = getNodeByPrefix(tmp.next, prefix);
        }

        if(prefix.length() == 0 && ta.index!=-1){
            tmp.next = getNewList(tmp.next, ta.index);
            return true;
        }
        System.out.println();
        return false;

    }

    private List<Node> getNewList(List<Node> nodes, int index){
        List<Node> l = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if(i!=index)
                l.add(nodes.get(i));
        }
        Collections.sort(l, comparator);
        return l;
    }

    private TypeAddition getNodeByPrefix(List<Node> nodes, String prefix){
        for (int i = 0; i < nodes.size(); i++) {
            if(prefix.indexOf(nodes.get(i).value) == 0)
                return new TypeAddition(i, false, nodes.get(i).value);
        }
        return new TypeAddition(-1, false, "");
    }

    public static List<String> getAllWords(){
        class Init{
            List<String> result = new ArrayList<>();
            public void recursion(String s, Node node){
                if(node == null){
                    return;
                }
                if(node.next == null){
                    result.add(s);
                    return;
                }
                for (Node n : node.next) {
                    recursion(s+n.value, n);
                }

            }
        }
        Init i = new Init();
        i.recursion("", root);
        return i.result;
    }

    public boolean remove(String word){
        return true;
    }

    public void addAll(String... words){
        for (String s:words) {
            add(s);
        }
    }

    public static int getSizeWords(){
        return sizeWords;
    }

    private List<Node> getNewList(Node node){
        List<Node> l = node.next;
        if(node.next == null)
            l = new ArrayList<>();
        return l;
    }

    private String getEqualsePart(String s, String word){
        String sub = word.charAt(0)+"";
        while (s.indexOf(sub) == 0 && s.length() > sub.length()){
            sub+=word.charAt(sub.length());
        }
        return sub.substring(0, sub.length()-1);
    }

    private TypeAddition getTypeAddition(List<Node> l, String word){
        String substring = "";
        int index = -1;

        for (int i = 0; i < l.size(); i++) {
            String s = l.get(i).value;
            if(word.indexOf(s) == 0)
                return new TypeAddition(i, false, "");
            else {
                if(s.equals(word))
                    return new TypeAddition(-1, false, "");
                String sub = getEqualsePart(s, word);
                if(sub.length() != 0){
                    if(sub.length() > substring.length()) {
                        index = i;
                        substring = sub;
                    }
                }
            }
        }
        return new TypeAddition(index, true, substring);
    }
    private void defultAdd(Node n, String word){
        List<Node> next = new ArrayList<>(getNewList(n));
        next.add(new Node(word));
        Collections.sort(next, comparator);
        n.next = next;
    }
    private void splitAdd(Node tmp, int sizePart, String word){
        List<Node> next = new ArrayList<>();
        next.add(new Node(tmp.value.substring(sizePart), tmp.next));
        next.add(new Node(word.substring(sizePart)));
        Collections.sort(next, comparator);
        tmp.value = tmp.value.substring(0, sizePart);
        tmp.next = next;
    }

    public void printTree(){
        printTree(root, "");
    }

    private void printTree(Node node, String s){
        if(node == null)
            return;
        if(node.next == null){
            System.out.println(s);
            sizeWords++;
            return;
        }

        for (Node n:node.next) {
            printTree(n, s+" "+n.value);
        }
    }


}

