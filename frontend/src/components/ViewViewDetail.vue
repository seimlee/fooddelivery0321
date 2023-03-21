<template>

    <v-card outlined>
        <v-card-title>
            View # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="Customerid" v-model="item.customerid" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Menuid" v-model="item.menuid" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Quantity" v-model="item.quantity" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'ViewViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/views/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

