ConnectMan
===

### Description
A tool for gaining databases in mysql or sqlserver


### Usage
get the jdbc connection:
<code>
	ConnectMan.INSTANCE.getConnection([connectionProperties])
</code>


get all databases through the connection:
<code>
	ConnectMan.INSTANCE.getAllDatabases([connectionProperties])
</code>


### About ConnectionProperties
`ConnectionProperties` is a prifix of a key in property file which is in the `resources` folder.

such as 'mysql_fun' etc.
