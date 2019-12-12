package okex

import (
	"fmt"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestLoadPagingResult(t *testing.T) {
	r := []interface{}{}
	items := []map[string]string{}
	items = append(items, map[string]string{
		"a": "100",
		"b": "200",
	})

	pageInfo := map[string]string{}
	pageInfo["OK-BEFORE"] = "12121312312"
	pageInfo["OK-AFTER"] = "121143253456"

	r = append(r, items)
	r = append(r, pageInfo)
	pR, e := LoadPagingResult(r)
	require.True(t, pR != nil, pR)
	require.True(t, e == nil, e)
	fmt.Printf("%+v\n", *pR)
}
