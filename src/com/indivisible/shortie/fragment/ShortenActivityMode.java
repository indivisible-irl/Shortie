package com.indivisible.shortie.fragment;

/**
 * Enum to control Fragments loaded for main Activity.<br/>
 * <ul>
 * <li><strong>VIEW</strong> - Default mode: list links, manual input of new</li>
 * <li><strong>EDIT</strong> - Edit mode: edit existing links</li>
 * <li><strong>DELETE</strong> - Delete mode: batch remove existing links</li>
 * <li><strong>SEARCH</strong> - Search mode: Filter existing links by Service
 * and text</li>
 * </ul>
 * 
 * @author indiv
 */
public enum ShortenActivityMode
{
    VIEW,      //  SubmitInput, default
    EDIT,       //  Rows show edit icon
    DELETE,     //  Cancel, Delete, MultiSelect, Delete icon toggle (colour/grey)
    SEARCH;     //  SearchInput, ShortenSpinner, Instant filter
}
