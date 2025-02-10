package me.BRZeph.AI.GameData.Utils;

import me.BRZeph.AI.Core.Action;
import me.BRZeph.AI.Core.State;
import me.BRZeph.utils.GlobalUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static me.BRZeph.utils.Constants.Constants.LocalPaths.QTABLE_EXPORT_NAME;
import static me.BRZeph.utils.Constants.Constants.LocalPaths.QTABLE_EXPORT_PATH;


public class QTableSaver {

    // Save the QTable to an XML file with a unique name
    public void saveQTable(HashMap<State, HashMap<Action, Double>> qTable) {
        try {
            GlobalUtils.consoleLog("[INFO]: Saving QTable...");

            Document document = createXMLDocument(qTable);

            File file = getUniqueFile();

            writeXMLToFile(document, file);

            System.out.println("QTable saved to: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create the XML document with state and action data
    private Document createXMLDocument(HashMap<State, HashMap<Action, Double>> qTable) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element rootElement = document.createElement("QTable");
        document.appendChild(rootElement);

        for (State state : qTable.keySet()) {
            Element stateElement = createStateElement(document, state, qTable.get(state));
            rootElement.appendChild(stateElement);
        }
        return document;
    }

    private Element createStateElement(Document document, State state, HashMap<Action, Double> actions) {
        Element stateElement = document.createElement("State");

        Element stateKey = document.createElement("stateKey");
        stateKey.appendChild(document.createTextNode(state.toString()));
        stateElement.appendChild(stateKey);

        if (actions != null) {
            for (Action action : actions.keySet()) {
                Element actionElement = createActionElement(document, action, actions.get(action));
                stateElement.appendChild(actionElement);
            }
        } else {
            GlobalUtils.consoleLog("[INFO]: No actions for state: " + state);
        }

        return stateElement;
    }

    private Element createActionElement(Document document, Action action, Double qValue) {
        Element actionElement = document.createElement("Action");

        Element actionKey = document.createElement("actionKey");
        actionKey.appendChild(document.createTextNode(action.toString()));
        actionElement.appendChild(actionKey);

        Element qValueElement = document.createElement("QValue");
        qValueElement.appendChild(document.createTextNode(qValue.toString()));
        actionElement.appendChild(qValueElement);

        return actionElement;
    }

    private File getUniqueFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        File folder = new File(QTABLE_EXPORT_PATH);
        if (!folder.exists()) {
            folder.mkdirs();  // Create the folder if it doesn't exist
        }

        String newFileName = date + "_" + QTABLE_EXPORT_NAME;
        int counter = 1;
        File file = new File(folder, newFileName + ".xml");

        while (file.exists()) {
            newFileName = date + "_" + QTABLE_EXPORT_NAME + "_" + counter;
            file = new File(folder, newFileName + ".xml");
            counter++;
        }

        return file;
    }

    private void writeXMLToFile(Document document, File file) throws Exception {
        try (FileWriter writer = new FileWriter(file)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        }
    }
}
