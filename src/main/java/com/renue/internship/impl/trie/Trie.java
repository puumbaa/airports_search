package com.renue.internship.impl.trie;


import java.util.*;

public class Trie {
    private final Node root;

    public int getDepth() {
        return depth;
    }

    private final int depth;
    private static final int MAX_DEPTH = 4;
    private boolean isNumberTypeTrie;

    public boolean isNumberTypeTrie() {
        return isNumberTypeTrie;
    }

    public void setNumberTypeTrie(boolean numberTypeTrie) {
        isNumberTypeTrie = numberTypeTrie;
    }

    public Trie(int depth) {
        validateDepth(depth);
        this.root = new Node('\0');
        this.depth = depth;
    }

    public Trie(Node root, int depth) {
        validateDepth(depth);
        this.root = root;
        this.depth = depth;
    }

    private static void validateDepth(int depth) {
        if (depth < 1 || depth > MAX_DEPTH) {
            throw new IllegalArgumentException(
                    String.format("Максимальная глубина должна быть в промежутке от 1 до %d", MAX_DEPTH)
            );
        }
    }

    public void insert(String word, int offset) {
        Node root = this.root;
        int bound = Math.min(word.length(), depth);
        for (int i = 0; i < bound; i++) {
            Node node = root.children.get(word.charAt(i));
            if (node != null) {
                node.offsets.add(offset);
            } else {
                Node newNode = new Node(word.charAt(i), offset);
                root.children.put(word.charAt(i), newNode);
                node = newNode;
            }
            root = node;
        }
    }

    public void clear() {
        root.children.clear();
    }


    public Set<Integer> hits(String prefix) {
        Node currentNode = this.root;
        int bound = Math.min(prefix.length(), depth);
        for (int i = 0; i < bound; i++) {
            Node nextNode = currentNode.children.get(prefix.charAt(i));
            if (nextNode == null) {
                return Collections.emptySet();
            } else {
                currentNode = nextNode;
            }
        }
        return currentNode.offsets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return depth == trie.depth && isNumberTypeTrie == trie.isNumberTypeTrie && Objects.equals(root, trie.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, depth, isNumberTypeTrie);
    }

    @Override
    public String toString() {
        return "Trie{" +
                "root=" + root +
                ", depth=" + depth +
                ", isNumberTypeTrie=" + isNumberTypeTrie +
                '}';
    }

    public static class Node {
        private final char c;
        private final Map<Character, Node> children;
        private final Set<Integer> offsets;

        public Node(char c) {
            this.c = c;
            children = new HashMap<>();
            offsets = new HashSet<>();
        }

        public Node(char c, Integer offset) {
            this(c);
            offsets.add(offset);
        }

        public Node(char c, Map<Character, Node> children) {
            this.c = c;
            this.children = children;
            this.offsets = new HashSet<>();
        }

        public Node(char c, Map<Character, Node> children, Integer offset) {
            this.c = c;
            this.offsets = new HashSet<>();
            this.children = children;
            offsets.add(offset);
        }

        public Node(char c, Map<Character, Node> children, Set<Integer> offsets) {
            this.c = c;
            this.children = children;
            this.offsets = offsets;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "c=" + c +
                    ", children=" + children +
                    ", offsets=" + offsets +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return c == node.c && Objects.equals(children, node.children) && Objects.equals(offsets, node.offsets);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, children, offsets);
        }
    }
}
