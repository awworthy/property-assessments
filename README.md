# README
## Property Assessments Final Project
## README Contents:
- Collaborators
- Core Project
- Custom Features

### Collaborators
- [Alex](https://github.com/awworthy)
- [Shea](https://github.com/odlands)
- [Dakota](https://github.com/DakotaDoolaege)

### Core Project
The core project is a property assessment management tool that uses .CSV files to provide data visualization and statistics tools for the user.

### Custom Features
* Custom File Selector
* Google API JavaScript Map
    * Heat Map of assessed values
    * Search map by neighbourhood
    * Search Map by Ward
* Data visualisation
    * Pie Chart with ranges of assessed values
        * Percentages displayed with mouse-over
    * Scatter plot with neighbourhoods and average property value
    * Bar Graph with wards and average property values
    * Ward and Neighbourhood searches will draw shapes on the map
        * This feature uses additional data sets to get the boundaries of each area
* Custom Class for Data Set options
    * Allows user to perform multiple consecutive searches to refine their results
    * Allows for all the tabs to share the same data 
    * The same search options on each tab
    * Selecting based on ward
    * Updates the data used by the heatmap and the charts and the table on each search
* Support for additional themes
    * Dark mode
* TABS
    * TabPane object implementation
    * Tabs closeability set to false to eliminate user error
    * Tabs are constructed with custom classes MapTab and DataTab
* CHALLENGES
    * Many of the issues we faced were due to how the data was collected, here are a few examples:
        * When incorporating the boundaries of the wards, we discovered that the 
        data collection was inconsistent with our original property assessments data set. For example, the first 
        ward was entered as "Ward 1" in our original data and in the ward boundaries data set, it was entered as 
        "Ward 01". The easiest work around for this was just reformatting the data of the boundaries data set so 
        that it matched the original.
        * When implementing visualization into our map tab, it became obvious once we were able to draw heat points 
        on properties and also draw boundaries around wards and neighborhoods that some of the properties had the 
        wrong wards entered. This caused problems for us when we wanted to show both the boundary and the heat map
        of a ward. The heat data would not always stay in the lines of our boundaries.
        * One of the earliest ideas we had was incorporating a feature that allowed the user to finde the closest
        three home renovations contractors to her property. We found a csv file on the city of Edmonton's website 
        that we thought we could use for this feature. Unfortunately, the addresses were inputted in a cell and 
        could contain any possible number of commas separating the suites, street number, street name, etc. The 
        comma issue was echoed with the cell that contained the company's name. This way of inputting data made 
        reading the file with our program almost impossible.
     

