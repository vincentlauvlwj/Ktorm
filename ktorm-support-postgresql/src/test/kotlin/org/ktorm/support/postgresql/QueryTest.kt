package org.ktorm.support.postgresql

import org.junit.Test
import org.ktorm.dsl.*
import org.ktorm.schema.TextSqlType
import java.sql.Clob
import kotlin.test.assertEquals

class QueryTest : BasePostgreSqlTest() {

    @Test
    fun testCast() {
        val salaries = database
            .from(Employees)
            .select(Employees.salary.cast(TextSqlType))
            .where { Employees.salary eq 200 }
            .map { row ->
                when (val value = row.getObject(1)) {
                    is Clob -> value.characterStream.use { it.readText() }
                    else -> value
                }
            }

        assertEquals(listOf("200"), salaries)
    }

    @Test
    fun testUnion() {
        val query = database
            .from(Employees)
            .select(Employees.id)
            .union(
                database.from(Departments).select(Departments.id)
            )
            .union(
                database.from(Departments).select(Departments.id)
            )
            .orderBy(Employees.id.desc())
            .limit(0, 4)

        println(query.sql)

        val results = query.joinToString { row -> row.getString(1).orEmpty() }
        assertEquals("4, 3, 2, 1", results)
    }

    @Test
    fun testUnionAll() {
        val query = database
            .from(Employees)
            .select(Employees.id)
            .unionAll(
                database.from(Departments).select(Departments.id)
            )
            .unionAll(
                database.from(Departments).select(Departments.id)
            )
            .orderBy(Employees.id.desc())

        println(query.sql)

        val results = query.joinToString { row -> row.getString(1).orEmpty() }
        assertEquals("4, 3, 2, 2, 2, 1, 1, 1", results)
    }
}
