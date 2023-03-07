package upo.additionalstructures;

// structure used for getMaxDisjointIntervals
public class WorkStruct {
	private Integer startTime;
	private Integer endTime;
	private Integer position;

	public Integer getStartTime() {
		return this.startTime;
	}
	public Integer getEndTime() {
		return this.endTime;
	}
	public Integer getPosition() {
		return this.position;
	}
	public WorkStruct(Integer start, Integer end, Integer position) {
		this.startTime = start;
		this.endTime = end;
		this.position = position;
	}	

}
