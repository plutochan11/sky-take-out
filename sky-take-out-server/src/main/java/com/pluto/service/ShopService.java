package com.pluto.service;

public interface ShopService {
	/**
	 * Switch shop status
	 * @param status The shop status
	 */
	void switchStatus(Short status);

	/**
	 * Get shop status
	 * @return The status where 1 stands for open and 0 stands for closed
	 */
	Short getStatus();
}
