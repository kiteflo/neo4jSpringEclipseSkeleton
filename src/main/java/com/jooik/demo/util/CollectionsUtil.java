package com.jooik.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 
 * Utility class containing static convenience methods for dealing with various 
 * sorts of collections (lists, collections, iterables...)
 * 
 */
public final class CollectionsUtil {
	
	/**
	 * @param iterable iterable
	 * @return list
	 */
	public static <E> List<E> asList(Iterable<E> iterable) {
		if (iterable == null) {
			return new ArrayList<E>();
		} else {
			return new ArrayList<E>(IteratorUtil.asCollection(iterable));
		}
	}
	
	/**
	 * @param iterable
	 * @return
	 */
	public static <E extends Comparable<E>> List<E> asSortedList(Iterable<E> iterable) {
		List<E> list = asList(iterable);
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Generate page excerpt from array based on the given array and the given
	 * page - page will provide information such as "pageNo: 2, itemsOnPage: 15",
	 * this enables us to determine the desired view area for the page and 
	 * setup a page based on this area...
	 * @param <T>
	 * @param page
	 * @param items
	 * @return
	 */
	public static <T> PageImpl<T> generatePage(Pageable page,List<T> items)
	{
		if (page != null)
		{
			int pageSize = page.getPageSize();
			int pageNo = page.getPageNumber();
			
			// calculate "from-to" borders...
			int fromItem = pageNo * pageSize;
			int toItem = fromItem + pageSize;
			
			// care for IndexOutOfBounding...
			if (toItem > items.size())
				toItem = items.size();
			
			List<T> pageExcerpt = new ArrayList<T>();
			for (int i=fromItem; i<toItem; i++)
			{
				pageExcerpt.add(items.get(i));
			}
			
			return new PageImpl<T>(pageExcerpt, page, items.size());
		}
		else
		{
			return new PageImpl<T>(items, page, items.size());
		}
	}
}
