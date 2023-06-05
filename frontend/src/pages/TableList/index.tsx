import type {ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import {DOCX_URL, PPTX_URL, XLSX_URL} from "@/pages/contants/indx";
import {deleteFile, searchFiles} from "@/services/ant-design-pro/api";
import {useRef} from "react";
import {ActionType} from "@ant-design/pro-table";
import {Button, message, Popconfirm, Upload} from "antd";
import {UploadChangeParam} from "antd/lib/upload";

const columns: ProColumns<API.CurrentFile>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '文档名称',
    align: 'center',
    width: 250,
    copyable: true,
    dataIndex: 'fileName',
  },
  {
    title: '文档地址',
    align: 'center',
    render: (_, record) => (
      <a href={record.fileUrl}>点击进入文档进行编辑</a>
    )
  },
  {
    title: '文档格式',
    align: 'center',
    width: 80,
    dataIndex: 'fileType',
    valueEnum: {
      'docx': <img src={DOCX_URL} width={50}/>,
      'pptx': <img src={PPTX_URL} width={50}/>,
      'xlsx': <img src={XLSX_URL} width={50}/>,
    },
  },
  {
    title: '操作',
    width: 200,
    key: 'option',
    align: 'center',
    valueType: 'option',
    render: (text, record, _, action) => [
      <a key="download" href={record.downloadUrl}>下载</a>,
      <a key="delete">
        <Popconfirm
          title="删除文件"
          onConfirm={async (e) => {
            console.log(e);
            console.log(record.deleteUrl);
            const deleteUrl = record.deleteUrl;
            const isDelete = await deleteFile({deleteUrl: deleteUrl});
            if (isDelete) {
              message.success('删除成功');
              // 刷新用户信息表单
              location.reload();
            } else {
              message.error('删除失败');
            }
          }}
          onCancel={(e) => {
          }}
          okText="Yes"
          cancelText="No"
        >
          <Button type="link" danger>
            删除
          </Button>
        </Popconfirm>
      </a>,
    ],
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  const handleUploadSuccess = () => {
    // 文件上传完成后刷新页面
    location.reload();
  };

  return (

    <ProTable<API.CurrentFile>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      rowKey="id"
      // @ts-ignore
      request={async (_, sort, filter) => {
        console.log(sort, filter);
        const fileList = await searchFiles();
        return {
          data: fileList,
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      cardProps={{title: '文档列表', bordered: true}}
      headerTitle={
        <Upload
          name="uploadedFile"
          action="http://localhost:8000/example/upload?"
          method="POST"
          onChange={(info: UploadChangeParam) => {
            if (info.file.status === 'done') {
              handleUploadSuccess();
            }
          }}
        >
          <Button type="primary">上传文件</Button>
        </Upload>
      }
      search={false}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
    />
  );
};
