package hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class HashAlgo {

	private final ConcurrentMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();
	
	public static void main(String[] args) {
		List<Node> nodes = new ArrayList<>();
		for(int i=0;i <3; i++) {
			nodes.add(new Node("node-" +i));
		}
		
		HashAlgo ha = new HashAlgo();
		for(int i=0;i < 20; i++) {
			ha.consistentHash(""+i, nodes);
		}
	}

	public void consistentHash(String key,List<Node> nodes) {
		ConsistentHashSelector selector = selectors.get(key);
		int identityHashCode = System.identityHashCode(key);
		if (selector == null) {
			selectors.put(key, new ConsistentHashSelector(nodes));
		}
		selector =  selectors.get(key);
		Node node = selector.select(key);
		System.out.println(key +"----" +node.getName());
	}

	private static final class ConsistentHashSelector {

		private final TreeMap<Long, Node> virtualNodes;

		private final int replicaNumber;

		private final int identityHashCode;

		private final int[] argumentIndex;

		public ConsistentHashSelector(List<Node> nodes) {
			this.virtualNodes = new TreeMap<Long, Node>();
			this.identityHashCode = System.identityHashCode(nodes);
			// URL url = nodes.get(0).getUrl();
			this.replicaNumber = 160;
			 String[] index ={"0"};
			// Constants.COMMA_SPLIT_PATTERN.split(url.getMethodParameter(methodName,
			// "hash.arguments", "0"));
			 argumentIndex = new int[index.length];
			// for (int i = 0; i < index.length; i ++) {
			// argumentIndex[i] = Integer.parseInt(index[i]);
			// }
			for (Node node : nodes) {
				for (int i = 0; i < replicaNumber / 4; i++) {
					byte[] digest = md5(node.getName() + "-" + i);
					for (int h = 0; h < 4; h++) {
						long m = hash(digest, h);
						virtualNodes.put(m, node);
					}
				}
			}
		}

		public int getIdentityHashCode() {
			return identityHashCode;
		}

		public Node select(String key) {
			byte[] digest = md5(key);
			Node invoker = sekectForKey(hash(digest, 0));
			return invoker;
		}

		private String toKey(Object[] args) {
			StringBuilder buf = new StringBuilder();
			for (int i : argumentIndex) {
				if (i >= 0 && i < args.length) {
					buf.append(args[i]);
				}
			}
			return buf.toString();
		}

		private Node sekectForKey(long hash) {
			Node invoker;
			Long key = hash;
			if (!virtualNodes.containsKey(key)) {
				SortedMap<Long, Node> tailMap = virtualNodes.tailMap(key);
				if (tailMap.isEmpty()) {
					key = virtualNodes.firstKey();
				} else {
					key = tailMap.firstKey();
				}
			}
			invoker = virtualNodes.get(key);
			return invoker;
		}

		private long hash(byte[] digest, int number) {
			return (((long) (digest[3 + number * 4] & 0xFF) << 24) | ((long) (digest[2 + number * 4] & 0xFF) << 16)
					| ((long) (digest[1 + number * 4] & 0xFF) << 8) | (digest[0 + number * 4] & 0xFF)) & 0xFFFFFFFFL;
		}

		private byte[] md5(String value) {
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
			md5.reset();
			byte[] bytes = null;
			try {
				bytes = value.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
			md5.update(bytes);
			return md5.digest();
		}

	}

	private static class Node {
		
		public Node(String name) {
			this.name=name;
		}
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}