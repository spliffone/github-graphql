package com.github.spliffone.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.github.spliffone.client.generated.TagsQuery
import com.github.spliffone.client.generated.test.TagsQuery_TestBuilder.Data
import org.junit.Test


internal class GithubClientTest {
    val createdAtString: String = "2022-05-16T19:49:50Z"

    fun apolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            //.addCustomScalarAdapter(GitTimestamp.type, com.github.spliffone.client.adapters.gitTimestampAdapter)
            .build()
    }

    @Test
    fun testGetTags() {
        val query = TagsQuery("test", "test", Optional.Present(100))
        //(testResolver = myTestResolver)
        val data = TagsQuery.Data {
            repository {
                refs {
                    totalCount = 1
                    refs = listOf(
                        ref {
                            id = "1"
                            name = "test-5.0.0.Alpha2"
                            target = tagTarget {
                                message = "test-5.0.0.Alpha2\\n"
                                tagger = tagger {
                                    createdAt = createdAtString
                                }
                                commit = commitCommit {
                                    __typename = "somet"
                                    committedDate = createdAtString
                                    zipballUrl = "https://codeload.github.com/test/test/legacy.zip/1"
                                }
                            }
                        }
                    )
                }
            }
        }

        val client = apolloClient()
        client.enqueueTestResponse(query, data)
    }
}