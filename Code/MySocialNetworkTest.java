import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MySocialNetworkTest {

    @Test
    void addPerson() {
        MySocialNetwork network = new MySocialNetwork();
        assertTrue(network.addPerson("Alice"));
        assertFalse(network.addPerson("Alice")); // Adding the same person again
    }

    @Test
    void removePerson() throws PersonNotFoundException {
        MySocialNetwork network = new MySocialNetwork();
        assertThrows(PersonNotFoundException.class, () -> network.removePerson("Alice")); // Removing from an empty graph
        network.addPerson("Alice");
        assertTrue(network.removePerson("Alice"));
        assertThrows(PersonNotFoundException.class, () -> network.removePerson("Alice")); // Removing a person not in the graph
    }

    @Test
    void connectPeople() {
        MySocialNetwork network = new MySocialNetwork();
        assertThrows(PersonNotFoundException.class, () -> network.connectPeople("Alice", "Bob")); // Connecting in an empty graph
        network.addPerson("Alice");
        network.addPerson("Bob");
        network.addPerson("Dung");
        assertDoesNotThrow(() -> network.connectPeople("Alice", "Bob")); // Connecting two existing people
        assertThrows(PersonNotFoundException.class, () -> network.connectPeople("Alice", "Charlie")); // Connecting with a person not in the graph
    }

    @Test
    void getConnections() throws PersonNotFoundException {
        MySocialNetwork network = new MySocialNetwork();
        assertThrows(PersonNotFoundException.class, () -> network.getConnections("Alice")); // Getting connections from an empty graph
        network.addPerson("Alice");
        network.addPerson("Bob");
        network.addPerson("Charlie");
        network.addPerson("Dung");
        network.addPerson("Lodu");
        network.addPerson("Gandu");
        network.connectPeople( "Bob","Alice");
        network.connectPeople( "Bob","Alice");
//        network.connectPeople( "Bob","Kavan");
        network.connectPeople("Alice", "Charlie");
        network.connectPeople("Charlie", "Dung");
        network.connectPeople("Alice", "Dung");
        network.connectPeople("Bob", "Lodu");
        network.connectPeople("Bob", "Gandu");
        List<String> connections = network.getConnections("Alice");
        List<String> connections1 = network.getConnections("Bob");
        List<String> expectedConnections = Arrays.asList("Bob","Charlie", "Dung");
        List<String> expectedConnections1 = Arrays.asList("Alice","Gandu","Lodu");
        assertEquals(expectedConnections, connections);
        assertEquals(expectedConnections1, connections1);
        assertFalse(connections.contains("Gandu"));
        assertFalse(connections.contains("Lodu"));
//        assertFalse(connections.contains("Dung"));
    }

    @Test
    void getMinimumDegreeOfSeparation() throws PersonNotFoundException {
        MySocialNetwork network = new MySocialNetwork();
        assertThrows(PersonNotFoundException.class, () -> network.getMinimumDegreeOfSeparation("Alice", "Bob")); // Getting separation from an empty graph
        network.addPerson("Alice");
        network.addPerson("Bob");
        network.addPerson("dung");
        network.addPerson("ella");
        network.addPerson("Lodu");
        network.addPerson("Gandu");
        network.addPerson("Harami");
        network.addPerson("Gatu");
        assertEquals(-1, network.getMinimumDegreeOfSeparation("Alice", "Bob")); // No connection yet
        network.connectPeople("Alice", "Bob");
        network.connectPeople("Bob","dung");
        network.connectPeople("dung","ella");
        network.connectPeople("ella", "Harami");
        network.connectPeople("Lodu", "Gandu");
        network.connectPeople("Bob","Gatu");
        network.connectPeople("dung","Gatu");
        assertEquals(1, network.getMinimumDegreeOfSeparation("Alice", "Bob")); // Connected directly
        assertEquals(2,network.getMinimumDegreeOfSeparation("Alice", "dung"));
        assertEquals(3,network.getMinimumDegreeOfSeparation("Alice", "ella"));
        assertEquals(3,network.getMinimumDegreeOfSeparation("ella", "Alice"));
        assertEquals(1,network.getMinimumDegreeOfSeparation("Gandu", "Lodu"));
        assertEquals(-1,network.getMinimumDegreeOfSeparation("Lodu", "Bob"));
        assertEquals(2,network.getMinimumDegreeOfSeparation("ella","Gatu"));
    }

    @Test
    public void testGetConnectionsToDegree() throws PersonNotFoundException {
        // Create a sample social graph
        MySocialNetwork socialGraph = new MySocialNetwork();
        socialGraph.addPerson("Alice");
        socialGraph.addPerson("Bob");
        socialGraph.addPerson("Charlie");
        socialGraph.addPerson("Eve");
        socialGraph.addPerson("Lodu");
        socialGraph.addPerson("Gandu");
        socialGraph.addPerson("Harami");
        socialGraph.addPerson("Gatu");

        socialGraph.connectPeople("Alice", "Bob");
        socialGraph.connectPeople("Alice", "Charlie");
        socialGraph.connectPeople("Bob", "Eve");

        // Test getConnectionsToDegree with maxLevel = 1
        List<String> connectionsMax1 = socialGraph.getConnectionsToDegree("Alice", 1);
        List<String> expectedConnectionsMax1 = Arrays.asList("Alice", "Bob", "Charlie");
        assertEquals(expectedConnectionsMax1, connectionsMax1);

        // Test getConnectionsToDegree with maxLevel = 2
        List<String> connectionsMax2 = socialGraph.getConnectionsToDegree("Alice", 2);
        List<String> expectedConnectionsMax2 = Arrays.asList("Alice", "Bob", "Charlie", "Eve");
        assertEquals(expectedConnectionsMax2, connectionsMax2);
    }

    @Test
    public void testAreWeAllConnected_ConnectedGraph() throws PersonNotFoundException {
        MySocialNetwork graph = new MySocialNetwork();

        // Adding vertices
        graph.addPerson("Alice");
        graph.addPerson("Bob");
        graph.addPerson("Charlie");
        graph.addPerson("David");

        // Adding edges to create a connected graph
        graph.connectPeople("Alice", "Bob");
        graph.connectPeople("Bob", "Charlie");
        graph.connectPeople("Charlie", "David");
        graph.connectPeople("Alice", "Charlie");

        assertTrue(graph.areWeAllConnected(), "Connected graph should return true");
    }

    @Test
    public void testAreWeAllConnected_DisconnectedGraph() throws PersonNotFoundException {
        MySocialNetwork graph = new MySocialNetwork();

        // Adding vertices
        graph.addPerson("Alice");
        graph.addPerson("Bob");
        graph.addPerson("Charlie");
        graph.addPerson("David");

        // Adding edges to create a disconnected graph
        graph.connectPeople("Alice", "Bob");
        graph.connectPeople("Charlie", "David");

        assertFalse(graph.areWeAllConnected(), "Disconnected graph should return false");
    }
}
