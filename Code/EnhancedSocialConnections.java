import java.util.List;

/**
 * A5 Problems 2 (20 bonus marks that will 'spill' to other assignments)
 * You can use P1's code as a starer (you should, to be honest)
 *
 * <p>
 * We are now enhancing our social graph to support affinity levels between two connections.
 * While there are multiple ways of achieving this from a design perspective, you will have to
 * implement a concrete subclass of EnhancedSocialConnections and modify your P1s methods accordingly
 * plus implement the extra methods defined here
 * </p>
 *
 */
public abstract class EnhancedSocialConnections implements SocialConnections{

    /**
     * Connects two individuals on the social graph given an affinity from 1-10 where 1 is the strongest
     * The method will return without changing the graph if firstPerson == secondPerson.
     * If there is already connection, the method will update the affinity level
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @param level the affinity level from 1-10 (inclusive). 1 is strongest, 10 is weakest
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    public abstract void connectPeople(String firstPerson, String secondPerson, int level) throws PersonNotFoundException;

    @Override
    /*
     * Nothing for you to do here ;)
     */
    public void connectPeople(String firstPerson, String secondPerson) throws PersonNotFoundException {
        connectPeople(firstPerson,secondPerson,10);
    }

    /**
     * Returns an ordered list (A-Z) containing the strongest connections to the individual.
     * E.g. the individual has three affinity 2 connections and five affinity 6 the method should only return a list if the three affinity 2 Individuals
     * @param name the person's name
     * @return and ordered list containing the strongest connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    public abstract List<String> getStrongestConnection(String name) throws PersonNotFoundException;

    /**
     * Returns a list containing the path from the first person to the second one with the strongest affinity.
     * THIS LIST CANNOT BE SORTED
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @return the path (names) between both individuals connecting them with the strongest affinity possible
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    public abstract List<String> getStrongestPath(String firstPerson, String secondPerson) throws PersonNotFoundException;

    /**
     * Returns a list containing the path from the first person to the second one with the weakest affinity overall.
     * THIS LIST CANNOT BE SORTED
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @return the path (names) between both individuals connecting them with the weakest affinity possible
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    public abstract List<String> getWeakestPath(String firstPerson, String secondPerson) throws PersonNotFoundException;
}
