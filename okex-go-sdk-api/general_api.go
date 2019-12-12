package okex

/*
 OKEX general api
 @author Tony Tian
 @date 2018-03-17
 @version 1.0.0
*/

/*
 Time of the server running OKEX's REST API.
*/
func (client *Client) GetServerTime() (ServerTime, error) {
	var serverTime ServerTime
	_, err := client.Request(GET, OKEX_TIME_URI, nil, &serverTime)
	return serverTime, err
}
