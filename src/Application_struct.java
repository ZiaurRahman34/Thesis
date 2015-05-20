	/**This method request service from the most trusted followee*/
	public synchronized Outcome request_Service() {
		MyOutcome outcome = null;
		ComparableFriend friend = rank_friends2();//get most trusted VE
		if (friend.value > threshold) {// then trust him
			outcome = get_followee(friend.id).requestService(service.id);
			if (outcome!=null)
				addNewShare(friend.id, outcome);
			return outcome;
		}
		return null;
	};
	/**This method returns a followee as recommendation to someone else*/
	public synchronized TemplateTRM_Sensor request_Sensor(boolean collusion, boolean malicious) {
		TemplateTRM_Sensor sensor = null;
		ComparableFriend friend = rank_friends2();
		if (friend.value > threshold) {
			sensor = get_followee(friend.id).Sensor;
		}
		if (malicious && collusion) {
			friend = rank_friends_reverse();
			if (friend.value<0.5)
				sensor = get_followee(friend.id).Sensor;
		}
		return sensor;
	};

	/**This method returns all frinds sorted by trust descending */
	public synchronized PriorityQueue<ComparableFriend> rank_friends(String list) {
		PriorityQueue<ComparableFriend> evaluationQeue;

		Comparator<ComparableFriend> comparator = new FriendComparator();
		evaluationQeue = new PriorityQueue<ComparableFriend>(1,
				comparator);
		ArrayList<String> keysAsArray = new ArrayList<String>(
				followees.keySet());
		if (list.equals("trust")){
			for (String id : keysAsArray){
				if (get_followee(id).Sensor.isActive())
		evaluationQeue.add(new ComparableFriend(id, get_followe(id).calculate_Trust()));}}
		else{

		for (String id : keysAsArray){
			if (get_followee(id).Sensor.isActive()){
		evaluationQeue.add(new ComparableFriend(id, get_followee(id).calculate_rec_Trust()));}}
		}
		return evaluationQeue;
	}

	/**This method returns the most trusted friend only*/
	public synchronized ComparableFriend rank_friends2() {// compute trust for all followees
		// and return higher trustee
		ComparableFriend selected = new ComparableFriend("no", -0.1);

		ArrayList<String> keysAsArray = new ArrayList<String>(
				followees.keySet());
		for (String id : keysAsArray) {
			if (get_followee(id).Sensor.isActive()){

				double ttrust = get_followee(id).calculate_Trust();
				if (ttrust > selected.value) {
					selected.id = id;
					selected.value = ttrust;
				}}}
		return selected;
	}

	/** This method adds a new Share to the collection of shares of this sensor */
	public synchronized void addNewShare(String id, MyOutcome outcome) { 
		Followee_struct f = get_followee(id);
		if (f!=null)//mporei na edwsa service se emena apo recommendation
			f.shares.addFirst(new MyTransaction(outcome));
	}


