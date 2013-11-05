package Topology;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.crypto.Cipher;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Utility.TimeInterval;

/**
 * This class is used to replace the Topology class. After this class is
 * finished. Topology.java should be replaced by this file.
 * 
 * @author Converse
 * 
 */

public class Topology {
	

	/**
	 * This number should be replaced when add checking road category in future.
	 */
	static int NumberOfLanes = 2;

	/**
	 * Key is the id of a node, value is CrossRoad which contains the coordinate info.
	 */
	static Hashtable<Long, Intersection> hashtableNodes = new Hashtable<Long, Intersection>();
	
	/**
	 * Key is the id of a node, value is the number of times this node appeared in map file.
	 * This hashmap is used for topology construction calculation.
	 */
	static private Hashtable<Long, Integer> hashtableCounter = new Hashtable<Long, Integer>();
	
	/**
	 * Map file position.
	 */
	static String MapPath;
	
	static Intersection preNode = null;
	
	public static Hashtable<Long, Intersection> getIntersections() {
		return hashtableNodes;
	}
	
	public static Intersection getIntersection(long id) throws Exception {
		if (hashtableNodes.containsKey(id))
		{
			return hashtableNodes.get(id);
		}
		else
		{
			throw new Exception("Can not find intersectoin:" + id);
		}
	}
	
	/**
	 * Test entry for class Topology.
	 * @param argv
	 * @throws Exception 
	 */
	public static void main(String argv[]) throws Exception {
		System.out.println("Start");
		

		ParseMapXMLGetIntersections();
		ParseMapXMLPopulateRoad();
		
		
		for (long theKey: hashtableNodes.keySet())
		{
			hashtableNodes.get(theKey).print();
		}
		System.out.println("Size:" + hashtableNodes.size());
	}
	
	public Topology(String mapPath)
	{
		System.out.println("Start to initialize topology...");
		MapPath = mapPath;
		System.out.println("Map path:" + MapPath);
		System.out.println("Getting all intersections from xml file");
		ParseMapXMLGetIntersections();
		System.out.println("Populating roads");
		ParseMapXMLPopulateRoad();
		System.out.println("Topology constructed. Number of nodes:" +
				hashtableNodes.size());
	}
	
	/**
	 * Parse the XML file and form the relation between
	 * intersections.
	 */
	private static void ParseMapXMLPopulateRoad()
	{
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					
					if (qName.equalsIgnoreCase("way")) 
					{
						preNode = null;
					}
					
					if (qName.equalsIgnoreCase("nd")) 
					{
						long reference = Long.valueOf(attributes.getValue(0));
						if (hashtableNodes.containsKey(reference))
						{
							if (preNode == null)
							{
								preNode = hashtableNodes.get(reference);
							}
							else
							{
								preNode.AddNeighbor(hashtableNodes.get(reference), NumberOfLanes, 30);
								hashtableNodes.get(reference).AddNeighbor(preNode, NumberOfLanes, 30);
								preNode = hashtableNodes.get(reference);
							}
						}
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

				}
			};
			saxParser.parse(MapPath, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// remove the nodes which only appears in nodes declaration.
		LinkedList<Long> removeList = new LinkedList<Long>();
		for (long theKey : hashtableNodes.keySet())
		{
			if (hashtableNodes.get(theKey).GetNumberOfNeighbors() == 0)
			{
				removeList.add(theKey);
			}
		}
		for (long theKey : removeList)
		{
			hashtableNodes.remove(theKey);
		}
	}
	
	/**
	 * Parse the xml file and get all the crossroads.
	 */
	private static void ParseMapXMLGetIntersections()
	{
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("bounds")) {
						int k = attributes.getLength();
						System.out.println("Map boundries:");
						for (int i = 0; i < k; i++)
						{
							if (attributes.getLocalName(i).compareToIgnoreCase("minlat") == 0)
								System.out.println("MinLat: " + attributes.getValue(i));
							if (attributes.getLocalName(i).compareToIgnoreCase("maxlat") == 0)
								System.out.println("MaxLat: " + attributes.getValue(i));
							if (attributes.getLocalName(i).compareToIgnoreCase("minlon") == 0)
								System.out.println("MinLon: " + attributes.getValue(i));
							if (attributes.getLocalName(i).compareToIgnoreCase("maxlon") == 0)
								System.out.println("MaxLon: " + attributes.getValue(i));
						}
					}

					if (qName.equalsIgnoreCase("node")) {
						int k = attributes.getLength();
						long id = 0;
						double lat = 0;
						double lon = 0;
						for (int i = 0; i < k; i++)
						{
							if (attributes.getLocalName(i).compareToIgnoreCase("id") == 0)
							{
								id = Long.valueOf(attributes.getValue(i));
								continue;
							}
							if (attributes.getLocalName(i).compareToIgnoreCase("lat") == 0)
							{
								lat = Double.valueOf(attributes.getValue(i));
								continue;
							}
							if (attributes.getLocalName(i).compareToIgnoreCase("lon") == 0)
							{
								lon = Double.valueOf(attributes.getValue(i));
								continue;
							}
						}
						hashtableNodes.put(id, new Intersection(lat, lon, id));
					}
					
					if (qName.equalsIgnoreCase("nd")) 
					{
						long reference = Long.valueOf(attributes.getValue(0));
						if (hashtableCounter.containsKey(reference)) {
							int value = hashtableCounter.get(reference);
							hashtableCounter.put(reference, value + 1);
						} else {
							hashtableCounter.put(reference, 1);
						}
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

				}
			};
			saxParser.parse(MapPath, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// remove the nodes which only appears once in way's nd.
		LinkedList<Long> removeList = new LinkedList<Long>();
		for (long theKey : hashtableCounter.keySet())
		{
			if (hashtableCounter.get(theKey) <= 1)
			{
				removeList.add(theKey);
			}
		}
		for (long theKey : removeList)
		{
			hashtableNodes.remove(theKey);
		}
	}
	
	public double DistanceBetween(long city1, long city2) throws Exception
	{
		return getIntersection(city1).GetNeighborById(city2).getDistance();
	}
	
	public int NumberOfIntersections()
	{
		return this.hashtableNodes.size();
	}
	
	public void AddReservation(long city1, long city2, TimeInterval time) throws Exception
	{
		getIntersection(city1).GetNeighborById(city2).AddReservation(time);
	}
	
	public void RemoveReservation(long city1, long city2, TimeInterval time) throws Exception
	{
		getIntersection(city1).GetNeighborById(city2).RemoveReservation(time);
	}
	
	public void ReleaseUsingRoad(long city1, long city2) throws Exception
	{
		getIntersection(city1).GetNeighborById(city2).LeaveRoad();
	}
	
	/**
	 * 
	 * @param city1
	 * @param city2
	 * @return - Delay in current traffic condition.
	 * @throws Exception 
	 */
	public TimeInterval AcquireUsingRoad(long city1, long city2) throws Exception
	{
		return getIntersection(city1).GetNeighborById(city2).UseRoad();
	}
	
	public TimeInterval EstimateDelayBetween(long city1, long city2, TimeInterval time) throws Exception
	{
		return getIntersection(city1).GetNeighborById(city2).getCurrentDelay();
	}
	
	public TimeInterval CurrentDelayBetween(long city1, long city2) throws Exception
	{
		return getIntersection(city1).GetNeighborById(city2).getCurrentDelay();
	}
}
