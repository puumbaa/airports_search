package com.renue.internship.impl.trie;


import java.util.*;

public class Trie {
    private final Node root;

    public int getMaxDepth() {
        return maxDepth;
    }

    private final int maxDepth;

    private boolean isNumberTypeTrie;

    public boolean isNumberTypeTrie() {
        return isNumberTypeTrie;
    }

    public void setNumberTypeTrie(boolean numberTypeTrie) {
        isNumberTypeTrie = numberTypeTrie;
    }

    public Trie(int maxDepth) {
        this.root = new Node('\0');
        this.maxDepth = maxDepth;
    }

    public void insert(String word, int offset) {
        Node root = this.root;
        int bound = Math.min(word.length(), maxDepth);
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


    public Set<Integer> hits(String prefix) {
        Node currentNode = this.root;
        int bound = Math.min(prefix.length(), maxDepth);
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

    private static class Node {
        private final char c;
        private final Map<Character, Node> children;
        private final Set<Integer> offsets;

        public Node(char c) {
            this.c = c;
            children = new HashMap<>();
            offsets = new HashSet<>();
        }

        public Node(char c, Integer lineNumber) {
            this(c);
            offsets.add(lineNumber);
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
