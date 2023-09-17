+ Bag {
	sortedCounts {
		var answer = SortedList(8) { | x y | x >= y };
		this.contents.associationsDo { | anAssociation |
			answer.add(anAssociation.value -> anAssociation.key)
		};
		^answer.array
	}
}

+ Collection {
	countStats {
		var answer = this.asSet.asArray.collect { |uniqueItem, uniqueIndex|
			Dictionary[\uniqueItem->uniqueItem, \occurrence->this.occurrencesOf(uniqueItem)]
		}.sortBy(\occurrence)
		.reverse.collect { |item| item.atAll([\uniqueItem, \occurrence]).asEvent }
		.do {|item| item.postcs };
		^answer
	}
}