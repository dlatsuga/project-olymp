package org.pantheon.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static Map<Attribute, Queue<String>> pathToElements = new HashMap<>();
    private static Attribute attributeOfElementToSearch = new Attribute("id", "make-everything-ok-button");
    private static boolean elementWithAttributeFound = false;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String inputString;
        boolean isURLCorrect;

        do {
            System.out.println("Please type valid URL or Exit");
            inputString = bufferedReader.readLine();

            if ("EXIT".equals(inputString.toUpperCase())) {
                return;
            }
//            "https://agileengine.bitbucket.io/keFivpUlPMtzhfAy/samples/sample-0-origin.html"
            Pattern p = Pattern.compile("(https://)?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?+%/.\\w]+)?");
            Matcher m = p.matcher(inputString);
            isURLCorrect = m.matches();
        } while (!isURLCorrect);

        System.out.println("Start");
        Document document = Jsoup
                .connect(inputString)
                .get();

        List<Node> nodes = document.childNodes();
        searchElementByAttribute(nodes, attributeOfElementToSearch);

        if (!pathToElements.isEmpty()) {
            System.out.println("Found element with Attribute : ");
            System.out.println("***********************************");
            pathToElements.forEach((k, v) -> {
                System.out.println("Attribute : " + k);
                System.out.println("Path : " + v);
            });
        }
        System.out.println("End");
    }

    private static void searchElementByAttribute(List<Node> nodes, Attribute attributeToSearch) {
        for (Node node : nodes) {
            if (elementWithAttributeFound) {
                return;
            }

            if (node.attributes().size() > 0) {
                checkAttributesInNode(node, attributeToSearch);
            }

            List<Node> childNodes = node.childNodes();
            if (!childNodes.isEmpty()) {
                searchElementByAttribute(childNodes, attributeToSearch);
            }
        }
    }

    private static void checkAttributesInNode(Node node, Attribute attributeToSearch) {
        for (Attribute attribute : node.attributes()) {
            if (attributeToSearch.getKey().equals(attribute.getKey()) &&
                    attributeToSearch.getValue().equals(attribute.getValue())) {
                elementWithAttributeFound = true;
                pathToElements.put(attribute, pathToElementWithAttribute(node));
            }
        }
    }

    private static Queue<String> pathToElementWithAttribute(Node node) {
        Deque<String> path = new ArrayDeque<>();
        do {
            path.push(node.nodeName());
            node = node.parent();
        } while (!"#document".equals(node.nodeName()));
        return path;
    }
}