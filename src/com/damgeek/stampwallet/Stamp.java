package com.damgeek.stampwallet;

import java.util.UUID;

public class Stamp {
	private UUID stampId;
	private StampStatus status;
	public enum StampStatus {
		VALID, USED, EXPIRED
	};
	
	public Stamp() {
		setStampId(UUID.randomUUID());
		setStatus(StampStatus.VALID);
	}

	public StampStatus getStatus() {
		return status;
	}

	public void setStatus(StampStatus status) {
		this.status = status;
	}

	public UUID getStampId() {
		return stampId;
	}

	public void setStampId(UUID stampId) {
		this.stampId = stampId;
	}
	
	
}
