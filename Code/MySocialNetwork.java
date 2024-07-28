import java.util.*;

/**
 * Represents a social network and operations around it. It utilizes an unweighted graph to model connections between people.
 * Names are unique within the graph.
 */
public class MySocialNetwork implements SocialConnections {
    private Map<String, Set<String>> graph;

    //Constructs an empty social network.
    public MySocialNetwork() {
        this.graph = new HashMap<>();
    }

    /**
     * Adds a new person to the social graph. Names are unique i.e., can be used as keys.
     * @param name the name of the individual
     * @return returns true if the person was added; false if the person was already in the graph
     */
    @Override
    public boolean addPerson(String name) {
        if (graph.containsKey(name)) {
            System.out.println("Person already exists!");
            return false; // Person already exists
        }
        graph.put(name, new HashSet<>());
        return true;
    }

    /**
     * Removes a person from the social graph.
     * @param name the name of the individual to be removed
     * @return true if the person was successfully removed; throws a PersonNotFoundException otherwise
     * @throws PersonNotFoundException if the person is not found in the graph
     */
    @Override
    public boolean removePerson(String name) throws PersonNotFoundException {
        if ( !graph.containsKey(name) ) {
            throw new PersonNotFoundException( name +  " not found" );
        }
        graph.remove(name);
        // Remove all references to this person in other people's connections
        for ( Map.Entry<String, Set<String>> entry : graph.entrySet() ) {
            entry.getValue().remove(name);
        }
        return true;
    }

    /**
     * Connects two people in the social graph.
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @throws PersonNotFoundException if any of the specified people are not found in the graph
     */
    @Override
    public void connectPeople(String firstPerson, String secondPerson) throws PersonNotFoundException {
        if ( !graph.containsKey(firstPerson) ) {
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        if ( !graph.containsKey(secondPerson) ) {
            throw new PersonNotFoundException(secondPerson + " not found");
        }
        if ( firstPerson.equals(secondPerson) || graph.get(firstPerson).contains(secondPerson) ) {
            System.out.println("Connection already exists between " + firstPerson + " and "+ secondPerson);
            return; // Connection already exists or connecting same person
        }
        graph.get(firstPerson).add(secondPerson);
        graph.get(secondPerson).add(firstPerson);
    }

    /**
     * Returns a sorted list of first-degree connections of a person.
     * @param name the name of the person whose connections are to be retrieved
     * @return a sorted list of first-degree connections
     * @throws PersonNotFoundException if the specified person is not found in the graph
     */
    @Override
    public List<String> getConnections(String name) throws PersonNotFoundException {
        if ( !graph.containsKey(name) ) {
            throw new PersonNotFoundException(name + " not found");
        }

        List<String> connections = new ArrayList<>();
        Set<String> firstDegreeConnections = graph.get(name); // Get all connections of the person

        for ( Map.Entry<String, Set<String>> entry : graph.entrySet() ) {
            if( entry.getValue().contains(name) ){
                connections.add(entry.getKey().toString());
            }
        }

        Collections.sort(connections);
        return connections;
    }

    /**
     * Gets the minimum degree of separation between two individuals in the social graph.
     * @param firstPerson  the name of the first individual
     * @param secondPerson the name of the second individual
     * @return the minimum degree of separation between both individuals; -1 if they are not connected
     * @throws PersonNotFoundException if any of the specified people are not found in the graph
     */
    @Override
    public int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException {
        // Implementation of breadth-first search (BFS) to find minimum degree of separation
        if ( !graph.containsKey(firstPerson) ) {
            throw new PersonNotFoundException(firstPerson + " not found");
        }
        if ( !graph.containsKey(secondPerson) ) {
            throw new PersonNotFoundException(secondPerson + " not found");
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();

        queue.add(firstPerson);
        distance.put(firstPerson, 0);

        while ( !queue.isEmpty() ) {
            String current = queue.poll();
            if ( current.equals(secondPerson) ) {
                return distance.get(current);
            }
            for ( String neighbor : graph.get(current) ) {
                if ( !distance.containsKey(neighbor) ) {
                    distance.put(neighbor, distance.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
        return -1; // No connection found
    }

    /**
     * Gets a list of connections of a given individual up to a certain degree of separation.
     * @param name     the name of the person
     * @param maxLevel the maximum degree of separation (inclusive)
     * @return a sorted list of connections up to the specified degree of separation
     * @throws PersonNotFoundException if the specified person is not found in the graph
     */
    @Override
    public List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException {
        if ( !graph.containsKey(name) ) {
            throw new PersonNotFoundException(name + " not found");
        }

        List<String> connections = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();

        queue.add(name);
        visited.add(name);
        distance.put(name, 0);

        while ( !queue.isEmpty() ) {
            String current = queue.poll();
            int currentDistance = distance.get(current);
            if ( currentDistance <= maxLevel ) {
                connections.add(current);
            }
            if ( currentDistance < maxLevel ) {
                for ( String neighbor : graph.get(current) ) {
                    if ( !visited.contains(neighbor) ) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                        distance.put(neighbor, currentDistance + 1);
                    }
                }
            }
        }

        Collections.sort(connections);
        return connections;
    }

    /**
     * Determines if everyone in the graph is connected to everyone else.
     * @return true if there is a path to everyone
     */
    @Override
    public boolean areWeAllConnected() {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        String start = graph.keySet().iterator().next(); // Choose arbitrary starting point

        queue.add(start);
        visited.add(start);

        while ( !queue.isEmpty() ) {
            String current = queue.poll();
            for ( String neighbor : graph.get(current) ) {
                if ( !visited.contains(neighbor) ) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == graph.size();
    }
}
